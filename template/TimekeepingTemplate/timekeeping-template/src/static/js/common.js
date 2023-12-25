export const validateHiragana = (value) => {
  return !value.match(/^[\u3041-\u309f\u3000・ー]+$/);
};

export const validateKatagana = (value) => {
  return !value.match(/^[ｦ-ﾟ]*$/);
};

export const validateHalfAlphaNum = (value) => {
  return !value.match(/^[0-9a-zA-Z]+$/);
};
export const validateNumber = (value) => {
  return !value.match(/^[0-9]+$/);
};

export const validatePassword = (value) => {
  return !value.match(
    /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?!.*\s).{8,15}$/
  );
};

export const validateEmail = (value) => {
  const emailRegex = /^[^\s@]+@rikkeisoft\.com$/;
  return !emailRegex.test(value);
};

export const validateLength = (value, minlength, maxlength) => {
  const valueLength = value.length;
    return minlength > valueLength  || maxlength < valueLength
};
