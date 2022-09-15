import SwiftUI
import shared

struct MainScreen: View {
    @State private var amountCurrency = ""
    
	var body: some View {
        VStack (alignment: .center) {
            Welcome()
            HStack {
                TextField(
                    "###.##",
                    text: $amountCurrency
                )
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .keyboardType(.decimalPad)
                .font(.title3)
                .padding(5)
                CurrencyPicker()
            }
            Button("Convert") {
                print($amountCurrency.wrappedValue)
            }.buttonStyle(CustomButton()).padding(.top, 5)
            ConvertedRateList()
        }
        .frame(
            maxWidth: .infinity,
            maxHeight: .infinity,
            alignment:.top
        )
        .padding(.all, 10)
	}
}

struct ConvertedRate {
    var name: String
    var value: String
}

struct Welcome: View {
    var body: some View {
        Text("Currency Converter")
            .multilineTextAlignment(.center)
            .font(.system(size: 22))
    }
}

struct CurrencyPicker: View {
    var currencies = ["USD", "UAH", "EUR", "JPY"]
    @State private var selectedFrameworkIndex = 0
    
    var body: some View {
        Picker(selection: $selectedFrameworkIndex, label: Text("")) {
            ForEach(0 ..< currencies.count, id: \.self) {
                Text(self.currencies[$0])
            }
        }
    }
}

struct ConvertedRateList: View {
    private var gridItemLayout = [GridItem(.flexible(), spacing: 0), GridItem(.flexible(), spacing: 0), GridItem(.flexible(), spacing: 0)]

    var convertedRates = [
        ConvertedRate(name: "ABC", value: "1323423423423423424.2344"),
           ConvertedRate(name: "USD", value: "123.2344"),
           ConvertedRate(name: "UAH", value: "123.2344"),
          ConvertedRate(name: "JPY", value: "123.2344")
       ]
    
    var body: some View {
        ScrollView {
            LazyVGrid(columns: gridItemLayout, spacing: 10) {
                ForEach(0..<convertedRates.count, id: \.self) { i in
                    VStack() {
                        Text(convertedRates[i].name) .font(.system(size: 14))
                            .foregroundColor(.black)
                            .bold()
                        Text(convertedRates[i].value)
                            .font(.system(size: 14))
                            .foregroundColor(.black)
                            .lineLimit(2)

                    }
                    .frame(width: 80, height: 80)
                    .padding()
                    .background(Color(red: 0.96, green: 0.96, blue: 0.96))
                    .cornerRadius(5)
                }
            }.padding(.top, 10)
        }
    }
}

#if DEBUG
struct MainScreen_Previews: PreviewProvider {
	static var previews: some View {
		MainScreen()
	}
}
#endif
