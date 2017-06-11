Feature: currency service reply with valid response

  Scenario: currency service reply with valid set of currencies
    Given call get supported currencies
    Then the result has values
    And the result contains USD
    
  Scenario: CurrencyConverterService reply with rate
    Given call currency service to get currency conversion rate from USD to USD
    Then the conversion rate is 1.0