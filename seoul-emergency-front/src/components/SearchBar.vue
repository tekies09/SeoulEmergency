<template>
  <div class="search">
      <b-input-group>
        <b-form-select v-model="$store.state.category" :options="$store.state.options" @change="onChange($store.state.category)"></b-form-select>

        <b-form-input type="search"></b-form-input>

        <b-button variant="light">
          <img src="../assets/search.svg" alt="Search Icon" class="search-icon">
        </b-button>
      </b-input-group>
  </div>
</template>

<script>
export default {
  methods: {
    onChange(categoryInput) {
      this.$store.state.category = categoryInput;
    }
  }
}
</script>

<style>
/* .custom-select {
  height: 44px;
} */

.search-icon {
  height: 30px;
}
</style>