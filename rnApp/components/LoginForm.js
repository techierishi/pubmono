//import liraries
import React, { Component } from "react";
import { View, TextInput, StyleSheet, StatusBar } from "react-native";
import { createStackNavigator, createAppContainer } from "react-navigation";
import AppButton from "./AppButton";

class LoginForm extends Component {
  onButtonPress = () => {
    // Alert.alert("Button has been pressed!");
    // console.log(this.props);
    this.props.navigation.navigate("Home");
  };

  render() {
    return (
      <View style={styles.container}>
        <StatusBar barStyle="light-content" />
        <TextInput
          style={styles.input}
          autoCapitalize="none"
          onSubmitEditing={() => this.passwordInput.focus()}
          autoCorrect={false}
          keyboardType="email-address"
          returnKeyType="next"
          placeholder="Mobile Number"
          placeholderTextColor="#999999"
        />

        <AppButton text="LOGIN" onPress={this.onButtonPress} />
      </View>
    );
  }
}

// define your styles
const styles = StyleSheet.create({
  container: {
    padding: 20
  },
  input: {
    height: 45,
    backgroundColor: "#DFDFDF",
    borderRadius: 50,
    marginBottom: 10,
    padding: 10,
    color: "#ee4b50"
  },
  buttonContainer: {
    backgroundColor: "#ee4b50",
    paddingVertical: 15,
    borderRadius: 50
  },
  labelext: {
    color: "#ee4b50",
    textAlign: "center",
    marginTop: 10,
    fontWeight: "700"
  },
  buttonText: {
    color: "#fff",
    textAlign: "center",
    fontWeight: "700"
  },
  loginButton: {
    backgroundColor: "#2980b6",
    color: "#fff"
  }
});

//make this component available to the app
export default LoginForm;
