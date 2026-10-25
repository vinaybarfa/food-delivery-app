export const isPresentInFavorites = (favorites, restaurant) => {
  for (let item of favorites) {
    if (restaurant.id === item.id) {
      // is item present in favorites or not
      return true;
    }
  }
  return false;
};
