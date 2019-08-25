type GetTokenRequest: void {
 .name: string
 .amount?: double
}

type GetTokenResponse: void {
  .sid: string
 }

type VerifyTokenRequest: void {
  .sid: string
}

type VerifyTokenResponse: void {
  .success: bool
 }

 type RefoundRequest: void {
  .name: string
  .message?: string
  .status?: string
 }

 type RefoundResponse: void {
  .success: bool
 }

interface BankInterface {
 RequestResponse: getToken(GetTokenRequest)(GetTokenResponse),
                  verifyToken(VerifyTokenRequest)(VerifyTokenResponse),
                  refound(RefoundRequest)(RefoundResponse)
}
