import SwiftUI

struct CustomButton: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .padding()
            .frame(height: 50)
            .background(Color(red: 0, green: 0, blue: 0.9))
            .foregroundColor(.white)
            .clipShape(Capsule())
    }
}
