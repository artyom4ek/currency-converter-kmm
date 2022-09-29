import Foundation
import SwiftUI
import shared

class CurrencyState: ObservableObject {
    private let viewModel = CurrencyViewModel()
    
    @Published private(set) var currencies = [Currency]()
    @Published private(set) var convertedRates = [ConvertedRate]()
    @Published private(set) var state: ViewState = .normal
    
    init() {
        viewModel.observeCurrencies() { items in
            self.currencies = items!
        }
        
        viewModel.observeConvertedRates() { items in
            self.convertedRates = items!
        }
        
        viewModel.observeState { state in
            self.state = state.toViewState()
        }
    }
    
    func fetchConvertedRates(amount: String, currency: String) {
        viewModel.fetchConvertedRates(enteredAmount: amount, selectedCurrency: currency)
    }
    
    deinit {
        viewModel.clear()
    }
}

private extension CurrencyViewModel.State {
    
    func toViewState() -> ViewState {
        switch self {
        case .normal: return .normal
        case .loading: return .loading
        case .success: return .success
        case .error: return .error
        case .empty: return .empty
        default: return .normal
        }
    }
}
