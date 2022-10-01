import SwiftUI
import shared

struct MainScreen: View {
    @ObservedObject private var viewModel = CurrencyState()
    
    @State private var amountCurrency = ""
    @State private var selectedCurrency = ""
    
    var body: some View {
        VStack (alignment: .center) {
            Welcome()
            HStack {
                TextField(
                    MR.strings().amount_placeholder.desc().localized(),
                    text: $amountCurrency
                )
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .keyboardType(.decimalPad)
                .font(.title3)
                .padding(5)
                CurrencyPicker(currencies: viewModel.currencies, onChanged: { value in
                    // Prevent initial function call
                    if !selectedCurrency.isEmpty {
                        viewModel.fetchConvertedRates(
                            amount: amountCurrency,
                            currency: value
                        )
                    }
                    selectedCurrency = value
                    
                })
            }
            Button(MR.strings().convert.desc().localized()) {
                viewModel.fetchConvertedRates(
                    amount: amountCurrency,
                    currency: selectedCurrency
                )
            }.buttonStyle(CustomButton()).padding(.top, 5)
            MainContent(
                state: viewModel.state, convertedRates: viewModel.convertedRates
            )
        }
        .frame(
            maxWidth: .infinity,
            maxHeight: .infinity,
            alignment: .top
        )
        .padding(.all, 10)
    }
}

struct Welcome: View {
    var body: some View {
        Text(MR.strings().app_name.desc().localized())
            .multilineTextAlignment(.center)
            .font(.system(size: 22))
    }
}

struct MainContent: View {
    var state: ViewState
    let convertedRates: [ConvertedRate]
    
    var body: some View {
        switch state {
        case ViewState.normal:
            ZStack{
                Text(MR.strings().info_ios.desc().localized())
                    .multilineTextAlignment(.center)
                    .font(.system(size: 16))
            }
            .frame(
                maxWidth: .infinity,
                maxHeight: .infinity,
                alignment:.center
            )
        case ViewState.loading:
            VStack {
                ProgressView().frame(alignment: .center)
            }
            .frame(
                maxWidth: .infinity,
                maxHeight: .infinity,
                alignment:.center
            )
        case ViewState.error:
            ZStack{
                Text(MR.strings().enter_correct_data.desc().localized())
                    .multilineTextAlignment(.center)
                    .foregroundColor(Color.red)
                    .font(.system(size: 16))
            }
            .frame(
                maxWidth: .infinity,
                maxHeight: .infinity,
                alignment:.center
            )
        case ViewState.success:
            ConvertedRateList(convertedRates: convertedRates)
        case ViewState.empty:
            ZStack{
                Text(MR.strings().empty_list.desc().localized())
                    .multilineTextAlignment(.center)
                    .foregroundColor(Color.red)
                    .font(.system(size: 16))
            }
            .frame(
                maxWidth: .infinity,
                maxHeight: .infinity,
                alignment:.center
            )
        }
    }
}

struct CurrencyPicker: View {
    var currencies: [Currency]
    var onChanged: (String) -> Void
    
    @State private var selectedFrameworkIndex = 0
    
    var body: some View {
        if !currencies.isEmpty {
            Picker(selection: $selectedFrameworkIndex, label: Text("")) {
                ForEach(0 ..< self.currencies.count, id: \.self) {
                    Text(self.currencies[$0].name)
                }
            }
            .onAppear {
                self.onChanged(currencies[0].name)
            }
            .onChange(of: selectedFrameworkIndex, perform: {
                index in self.onChanged(currencies[index].name)
            })
        }
    }
}

struct ConvertedRateList: View {
    var convertedRates: [ConvertedRate]
    
    var gridItemLayout = [GridItem(.flexible(), spacing: 0), GridItem(.flexible(), spacing: 0), GridItem(.flexible(), spacing: 0)]
    
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
