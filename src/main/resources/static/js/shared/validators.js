const regExp = new RegExp("^(https?|ftp):\\/\\/[^\\s/$.?#].[^\\s]*$");

const validateURI = (URL) =>
   URL !== undefined && URL !== null && URL !== "" && URL !== " " &&
   regExp.test(
      URL
   );