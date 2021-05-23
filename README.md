# Trades Application
This application helps to store Trades data.

## How to store data - RAML is not created yet
Use below REST API to add Trades data
1. REST End point `<HOST>/trades/add`
2. Request Type `PUT`
3. Request JSON - 
`[{
    "tradeId": "<String>",
    "version": <Integer>,
    "counterPartyId": "<String>",
    "bookId": "<String>",
    "maturityDate": "<String_In_dd/MM/yyyy>"
}]`


By default Trades data is stored into MongoDB.

## Maturity Date Expiry Update
Expired maturity date is automatically updated every night at 12AM.