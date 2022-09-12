import SwiftUI
import shared

struct MainScreen: View {
    var currencies = ["USD", "UAH", "EUR", "JPY"]
    @State private var amountCurrency = ""
    @State private var selectedFrameworkIndex = 0
    
	var body: some View {
        VStack (alignment: .center) {
            Text("Currency Converter")
                .multilineTextAlignment(.center)
                .font(.system(size: 22))
            HStack {
                TextField(
                    "###.##",
                    text: $amountCurrency
                )
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .keyboardType(.decimalPad)
                .font(.title3)
                .padding(5)
                
                Picker(selection: $selectedFrameworkIndex, label: Text("")) {
                    ForEach(0 ..< currencies.count, id: \.self) {
                        Text(self.currencies[$0])
                        
                    }
                }
            }
            Button("Convert") {
                print($amountCurrency.wrappedValue)
            }.buttonStyle(CustomButton())
        }
        .frame(
            maxWidth: .infinity,
            maxHeight: .infinity,
            alignment:.top
        )
        .padding(.all, 10)
	}
}

#if DEBUG
struct MainScreen_Previews: PreviewProvider {
	static var previews: some View {
		MainScreen()
	}
}
#endif
