//import liraries
import React, { Component } from "react";
import {
  View,
  Text,
  Image,
  StyleSheet,
  KeyboardAvoidingView
} from "react-native";
import LoginForm from "./LoginForm";

// create a component
class Login extends Component {
  render() {

    return (
      <KeyboardAvoidingView behavior="padding" style={styles.container}>
        <View style={styles.loginContainer} >
          <LoginForm navigation={this.props.navigation} />
        </View>
      </KeyboardAvoidingView>
    );
  }
}

// define your styles
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#FDFDFD"
  },
  loginContainer: {
    flexGrow: 1,
    justifyContent: "center"
  },
  logo: {
    position: "absolute",
    width: 300,
    height: 100
  },
  title: {
    color: "#FFF",
    marginTop: 120,
    width: 180,
    textAlign: "center",
    opacity: 0.9
  }
});

//make this component available to the app
export default Login;
