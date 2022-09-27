import Foundation
import SwiftUI
import shared

class CurrencyState: ObservableObject {
    let viewModel = CurrencyViewModel()
    
    @Published private(set) var currencies = [Currency]()
    @Published private(set) var state: ViewState = .normal
    
    init() {
        viewModel.observeCurrencies() { items in
            self.currencies = items!
        }
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
