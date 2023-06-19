import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel: ViewModel
    
    @State var text = "Loading..."
    @State var desc = "Loading..."
    @State var color = Color.red
    @State var selectedMode = 0
    let commManager = CommunicationManager()
    
    func load() {
        commManager.getData(completionHandler: { response, error in
            if let response = response {
                print(response)
                self.text = response.title
                self.desc = response.content
                switch (response.color) {
                case "primary":
                    self.color = Color.red
                case "secondary":
                    self.color = Color.blue
                case "tertiary":
                    self.color = Color.green
                default:
                    self.color = Color.yellow
                }
            } else {
                self.text = error?.localizedDescription ?? "error"
                self.desc = ""
            }
        })
    }
    
    private func listView() -> AnyView {
        switch viewModel.results {
        case .loading:
            return AnyView(Text("Loading...").multilineTextAlignment(.center))
        case .result(let res):
            return AnyView(List(res) { result in
                PostRow(post: result)
            })
        case .error(let description):
            return AnyView(Text(description).multilineTextAlignment(.center))
        }
    }

	var body: some View {
        NavigationView {
            VStack {
                listView()
                    .navigationBarTitle("Recent Posts")
                    .navigationBarItems(trailing:
                                            Button("Fresh load") {
                        self.viewModel.loadPosts(refresh: true)
                    })
                Picker("Which source shall be used?", selection: $selectedMode) {
                    Text("HTTP").tag(0)
                    Text("WIFI").tag(1)
                    Text("Bluetooth").tag(2)
                }
                .pickerStyle(.segmented)
                .onChange(of: selectedMode, perform: { newValue in
                    var newEnum = ActiveSource.http
                    if (newValue == 1) {
                        newEnum = ActiveSource.wifi
                    } else if (newValue == 2) {
                        newEnum = ActiveSource.bt
                    }
                    commManager.switchSource(source: newEnum)
                    load()
                })
                Text(text).onAppear() {
                    load()
                }
                Text(desc)
                self.color.frame(height: 20)
            }
            
        }
	}
}

extension ContentView {
    enum LoadableInfo {
        case loading
        case result([Post])
        case error(String)
    }
    
    @MainActor
    class ViewModel: ObservableObject {
        let cache: DataCache
        @Published var results = LoadableInfo.loading

        init(cache: DataCache) {
            self.cache = cache
            // no true required for initial load since database will be empty which will trigger fresh load
            self.loadPosts(refresh: false)
        }
        
        func loadPosts(refresh:Bool) {
            Task {
                do {
                    self.results = .loading
                    let newResults = try await cache.getPosts(reload: refresh)
                    
                    self.results = .result(newResults)
                } catch {
                    self.results = .error(error.localizedDescription)
                }
            }
        }
    }
}

extension Post: Identifiable { }
