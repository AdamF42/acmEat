type GetTokenRequest: void {
  .name: string
  .amount?: double
}

type GetTokenResponse: void {
  .status: string
  .sid: string
 }

type VerifyTokenRequest: void {
  .sid: string
}

type VerifyTokenResponse: void {
  .success: bool
 }

 type RefoundRequest: void {
  .sid: string
 }

 type RefoundResponse: void {
  .success: bool
 }

interface BankInterface {
  RequestResponse: getToken(GetTokenRequest)(GetTokenResponse),
                  verifyToken(VerifyTokenRequest)(VerifyTokenResponse),
                  refound(RefoundRequest)(RefoundResponse)
}
