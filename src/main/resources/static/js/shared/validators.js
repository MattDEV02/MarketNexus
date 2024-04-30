const validateObject = object => object !== undefined && object !== null && object !== "";

const validateArray = array => validateObject(array) && Array.isArray(array);

const validateString = string => validateObject(string);

const validateURI = (URI) =>
   validateString(URI) && URI !== " " &&
   new RegExp("^(https?|ftp):\\/\\/[^\\s/$.?#].[^\\s]*$").test(
      URI
   );