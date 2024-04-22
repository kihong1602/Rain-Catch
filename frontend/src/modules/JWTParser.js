import base64 from 'base-64'

export const JWTParser = () => {
  let token = sessionStorage.getItem('jwt');
  let payload = token.substring(token.indexOf('.') + 1, token.lastIndexOf('.'));
  let decode = JSON.parse(base64.decode(payload));
  return decode.sub;
}
