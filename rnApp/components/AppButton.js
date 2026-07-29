import React, { Component } from "react";
import { StyleSheet, Text, TouchableOpacity } from "react-native";
import PropTypes from "prop-types";

class AppButton extends Component {
  render() {
    const { text, onPress } = this.props;

    return (
      <TouchableOpacity
        style={styles.buttonContainer}
        onPress={() => onPress()}
      >
        <Text style={styles.buttonText}>{text}</Text>
      </TouchableOpacity>
    );
  }
}

AppButton.propTypes = {
  text: PropTypes.string.isRequired,
  onPress: PropTypes.func.isRequired
};

const styles = StyleSheet.create({
  buttonContainer: {
    backgroundColor: "#ee4b50",
    paddingVertical: 15,
    borderRadius: 50
  },
  buttonText: {
    color: "#fff",
    textAlign: "center",
    fontWeight: "700"
  }
});

export default AppButton;
