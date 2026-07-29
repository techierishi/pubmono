import { createStackNavigator, createAppContainer } from "react-navigation";
import Login from "./components/Login";
import HomePage from "./components/HomePage";
const AppNavigator = createStackNavigator(
  {
    Login: { screen: Login },
    Home: { screen: HomePage }
  },
  {
    initialRouteName: "Login"
  }
);

export default createAppContainer(AppNavigator);
