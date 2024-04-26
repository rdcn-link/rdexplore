export const setToken = (name,token) => {
  localStorage.setItem(name, token)
};

export const getToken = (name) => {
  return localStorage.getItem(name)
};

export const removeToken = (name) => {
  localStorage.removeItem(name)
};
