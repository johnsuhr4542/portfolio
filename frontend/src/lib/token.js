import jwt_decode from 'jwt-decode';

export const isValid = token => {
  try {
    const { exp } = jwt_decode(token);
    const now = new Date().getTime() / 1000;

    return exp - 180 > now;
  } catch (e) {
    console.log(e);
    return false;
  }
}