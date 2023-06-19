import SwiftUI
import shared

struct PostRow: View {
    var post: Post

    var body: some View {
        HStack() {
            VStack(alignment: .leading, spacing: 10.0) {
                Text("Post title: \(post.title)")
                .foregroundColor(Color.red)
                Text("Post details: \(post.body)")
            }
            Spacer()
        }
    }
}


