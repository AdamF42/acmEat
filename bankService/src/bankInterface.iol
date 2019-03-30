type ClientTokenRequest: void {
 .name: string
 .amount?: double
}

type TokenResponse: void {
  .sid: string
 }

type ClientToken: void {
  .sid: string
  .name?: string
}

type SuccessResponse: void {
  .success: bool
 }

 type RefoundRequest: void {
  .name: string
  .message?: string
  .status?: string
 }

interface BankInterface {
 RequestResponse: getToken(ClientTokenRequest)(TokenResponse),
                  verifyToken(ClientToken)(SuccessResponse),
                  refound(RefoundRequest)(SuccessResponse)
}
