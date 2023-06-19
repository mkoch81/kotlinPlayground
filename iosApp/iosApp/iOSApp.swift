import SwiftUI
import shared

@main
struct iOSApp: App {
    let cache = DataCache(databaseDriverFactory: DatabaseDriverFactory())
    
    var body: some Scene {
        WindowGroup {
            ContentView(viewModel: .init(cache: cache))
        }
    }
}
