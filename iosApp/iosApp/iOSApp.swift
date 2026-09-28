import SwiftUI
import Shared

@main
struct iOSApp: App {

    init() {
        KoinInitializer.shared.start()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}