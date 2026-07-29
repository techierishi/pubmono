<template>
  <div>
    <mt-card title="AB AB AB AB AB AB AB " minTitle="AB AB AB AB AB AB AB AB AB AB AB ">
      <p class="c-black f-500 m-b-20">AB AB </p>
      <mt-checkbox-group v-model="checkbox1">
        <mt-checkbox class="m-r-20" value="AB AB ">AB AB AB AB AB AB </mt-checkbox>
        <mt-checkbox class="m-r-20" value="AB AB ">AB AB AB AB AB AB </mt-checkbox>
        <mt-checkbox class="m-r-20" value="AB " disabled>AB AB AB AB AB </mt-checkbox>
        {{checkbox1}}
      </mt-checkbox-group>
      <mt-radio-group v-model="radio1">
        <mt-radio class="m-r-20" val="AB AB ">AB AB AB AB AB AB </mt-radio>
        <mt-radio class="m-r-20" val="AB AB ">AB AB AB AB AB AB </mt-radio>
        <mt-radio class="m-r-20" val="AB " disabled>AB AB AB AB AB </mt-radio>
        {{radio1}}
      </mt-radio-group>
       <mt-radio-group v-model="radio1">
        <mt-radio class="m-r-20" val="AB AB ">AB AB AB AB AB AB </mt-radio>
        <mt-radio class="m-r-20" val="AB AB ">AB AB AB AB AB AB </mt-radio>
        <mt-radio class="m-r-20" val="AB " disabled>AB AB AB AB AB </mt-radio>
        {{radio1}}
      </mt-radio-group>
      <p class="c-black f-500 m-b-5">inlineAB AB </p>
      <small>AB AB AB AB AB AB  inline AB AB </small><br><br>
      <mt-checkbox-group v-model="checkbox2" inline>
        <mt-checkbox class="m-r-20" value="AB AB ">AB AB </mt-checkbox>
        <mt-checkbox class="m-r-20" value="AB AB AB ">AB AB AB </mt-checkbox>
        <mt-checkbox class="m-r-20" value="Cherry" disabled>Cherry</mt-checkbox>
        {{checkbox2}}
      </mt-checkbox-group>
      <mt-radio-group v-model="radio2" inline>
        <mt-radio class="m-r-20" val="AB AB ">AB AB </mt-radio>
        <mt-radio class="m-r-20" val="AB AB ">AB AB </mt-radio>
        <mt-radio class="m-r-20" val="Pineapple" disabled>Pineapple</mt-radio>
        {{radio2}}
      </mt-radio-group>
      <p class="c-black f-500 m-b-5">stateAB AB </p>
      <small>AB AB AB AB AB AB  state AB AB </small><br><br>
      <mt-checkbox class="m-r-20 m-b-20" state="success" inline>AB AB </mt-checkbox>
      <mt-checkbox class="m-r-20 m-b-20" state="warning" inline>AB AB </mt-checkbox>
      <mt-checkbox class="m-r-20 m-b-20" state="error" inline>AB AB </mt-checkbox>
      <mt-radio class="m-r-20 m-b-20" state="success" inline>AB AB </mt-radio>
      <mt-radio class="m-r-20 m-b-20" state="warning" inline>AB AB </mt-radio>
      <mt-radio class="m-r-20 m-b-20" state="error" inline>AB AB </mt-radio>
    </mt-card>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        checkbox1: ['AB AB ','AB '],
        checkbox2: ['AB AB '],
        radio1: 'AB AB ',
        radio2: 'AB AB ',
      }
    }
  }
</script>

<style scoped>

</style>