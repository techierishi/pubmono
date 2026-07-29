<template>
  <div>
    <mt-card title="AB AB AB " minTitle="AB AB AB AB AB AB ">
      <mt-input 
        placeholder="inputSize=&quot;small&quot;" 
        inputSize="small"  
        :tipsText="`value=&quot;${inputSmall}&quot;`" 
        v-model="inputSmall"
      >
      </mt-input>
      <mt-input 
        placeholder="Default" 
        :tipsText="`value=&quot;${inputDefault}&quot;`" 
        v-model="inputDefault"
      >
      </mt-input>
      <mt-input 
        placeholder="inputSize=&quot;large&quot;" 
        inputSize="large"
        :tipsText="`value=&quot;${inputLarge}&quot;`" 
        v-model="inputLarge"
      >
      </mt-input>
      <div class="row">
        <div class="col-sm-4">
          <mt-input placeholder="col-sm-4"></mt-input>
        </div>
        <div class="col-sm-4">
          <mt-input placeholder="col-sm-4"></mt-input>
        </div>
        <div class="col-sm-4">
          <mt-input placeholder="col-sm-4"></mt-input>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-3">
          <mt-input placeholder="col-sm-3"></mt-input>
        </div>
        <div class="col-sm-3">
          <mt-input placeholder="col-sm-3"></mt-input>
        </div>
        <div class="col-sm-3">
          <mt-input placeholder="col-sm-3"></mt-input>
        </div>
        <div class="col-sm-3">
          <mt-input placeholder="col-sm-3"></mt-input>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6">
          <mt-input placeholder="col-sm-6"></mt-input>
        </div>
        <div class="col-sm-6">
          <mt-input placeholder="col-sm-6"></mt-input>
        </div>
      </div>
    </mt-card>
    <mt-card title="Form" minTitle="">
      <mt-input placeholder="Domain" inputSize="large" isFlag></mt-input>
      <mt-input placeholder="Username" inputSize="large"></mt-input>
      <mt-input placeholder="Password" inputSize="large" isFlag></mt-input>
      <mt-button bg="bluegray">Submit</mt-button>

    </mt-card>
    <mt-card title="IconAB AB AB AB AB AB " minTitle="AB AB AB AB AB  beforeIcon AB /AB  afterIcon AB AB ">
      <div class="row">
        <div class="col-sm-4">
          <mt-input placeholder="account" beforeIcon="account"></mt-input>
        </div>
        <div class="col-sm-4">
          <mt-input placeholder="local-phone" afterIcon="local-phone"></mt-input>
        </div>
        <div class="col-sm-4">
          <mt-input placeholder="email pin" beforeIcon="email" afterIcon="pin"></mt-input>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-4">
          <mt-input placeholder="camera" beforeIcon="camera"></mt-input>
        </div>
        <div class="col-sm-4">
          <mt-input placeholder="share" afterIcon="share"></mt-input>
        </div>
        <div class="col-sm-4">
          <mt-input placeholder="cloud-download folder" beforeIcon="cloud-download" afterIcon="folder"></mt-input>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-4">
          <mt-input placeholder="male-alt" beforeIcon="male-alt"></mt-input>
        </div>
        <div class="col-sm-4">
          <mt-input placeholder="female" afterIcon="female"></mt-input>
        </div>
        <div class="col-sm-4">
          <mt-input placeholder="AB AB " beforeIcon="male-female" afterIcon="run"></mt-input>
        </div>
      </div>
      <p class="c-black">FlagAB AB </p>
      <div class="row">
        <div class="col-sm-4">
          <mt-input placeholder="account" beforeIcon="account" isFlag></mt-input>
        </div>
        <div class="col-sm-4">
          <mt-input placeholder="local-phone" afterIcon="local-phone" isFlag></mt-input>
        </div>
        <div class="col-sm-4">
          <mt-input placeholder="email pin" beforeIcon="email" afterIcon="pin" isFlag></mt-input>
        </div>
      </div>
      <p class="c-black">AB AB AB AB </p>
      <mt-input placeholder="Small" inputSize="small" beforeIcon="email"></mt-input>
      <mt-input placeholder="Default" beforeIcon="email"></mt-input>
      <mt-input placeholder="Large" inputSize="large" beforeIcon="email"></mt-input>
    </mt-card>
    <mt-card title="AB AB AB AB " minTitle="AB AB AB AB AB  state AB AB ">
      <mt-input placeholder="success" state="success" tipsText="AB AB AB AB AB AB AB AB AB AB AB AB " isFlag></mt-input>
      <mt-input placeholder="warning" state="warning" tipsText="AB AB AB AB AB AB AB AB AB AB AB AB " isFlag></mt-input>
      <mt-input placeholder="error" state="error" tipsText="AB AB AB AB AB AB AB AB AB AB AB AB " isFlag></mt-input>
    </mt-card>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        inputSmall: 'Small',
        inputDefault: 'Default',
        inputLarge: 'Large',
      }
    },
  }
</script>

<style scoped>

</style>