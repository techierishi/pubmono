<template>
  <mt-card title="ABABAB" minTitle="ABABABABABABABABABABABAB，ABAB size ABABABABABABAB">
    <mt-dialog title="SmallABABAB" size="small" v-model="dialog1">ABABABABABABABABAB</mt-dialog>
    <mt-dialog title="ABABAB" v-model="dialog2">ABABABABABABABABABAB kira</mt-dialog>
    <mt-dialog title="LargeABABAB" size="large" v-model="dialog3">ABABABABABABABABAB</mt-dialog>
    <div class="btn-demo">
      <mt-button @click="click('dialog1')">small</mt-button>
      <mt-button @click="click('dialog2')">ABAB</mt-button>
      <mt-button @click="click('dialog3')">large</mt-button>
    </div>
    <p class="c-black f-500 m-t-15 m-b-20">ABABABABABABAB，ABAB confirmText AB cancelText ABAB</p>
    <mt-dialog title="ABABABABABABABABABAB" v-model="dialog4" confirmText="ABABABABAB" cancelText="ABABABABAB">ABABABABABABABABABABABABABAB</mt-dialog>
    <mt-button @click="click('dialog4')">ABABABABAB</mt-button>
    <p class="c-black f-500 m-t-20 m-b-20">ABABABABAB，ABAB bg ABAB</p>
    <mt-dialog title="ABABABABAB" v-model="dialog5" bg="blue">ABABABABABABABABAB</mt-dialog>
    <mt-dialog title="ABABABABAB" v-model="dialog6" bg="green">ABABABABABABABABAB</mt-dialog>
    <mt-dialog title="ABABABABAB" v-model="dialog7" bg="red">ABABABABABABABABAB</mt-dialog>
    <mt-dialog title="ABABABABAB" v-model="dialog8" bg="orange">ABABABABABABABABAB</mt-dialog>
    <mt-dialog title="ABABABABAB" v-model="dialog9" bg="bluegray">ABABABABABABABABAB</mt-dialog>
    <div class="btn-demo">
      <mt-button @click="click('dialog5')" bg="blue">BLUE</mt-button>
      <mt-button @click="click('dialog6')" bg="green">GREEN</mt-button>
      <mt-button @click="click('dialog7')" bg="red">RED</mt-button>
      <mt-button @click="click('dialog8')" bg="orange">ORANGE</mt-button>
      <mt-button @click="click('dialog9')" bg="bluegray">BLUE GRAY</mt-button>
    </div>
  </mt-card>
</template>

<script>
  export default {
    data() {
      return {
        dialog1: false,
        dialog2: false,
        dialog3: false,
        dialog4: false,
        dialog5: false,
        dialog6: false,
        dialog7: false,
        dialog8: false,
        dialog9: false
      }
    },
    methods: {
      click(val) {
        this[val] = true
      }
    }
  }
</script>

<style scoped>
  .btn-demo>button {
    margin: 0 5px 10px 0;
  }
</style>