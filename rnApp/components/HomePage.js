//import liraries
import React, { Component } from "react";
import {
  View,
  Text,
  TextInput,
  Alert,
  StyleSheet,
  StatusBar
} from "react-native";

import AppButton from './AppButton'

const onButtonPress = () => {
  Alert.alert("Button has been pressed!");
};

// create a component
class HomePage extends Component {
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
          placeholder="HomePage"
          placeholderTextColor="#999999"
        />



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
  labelext :{
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
export default HomePage;
