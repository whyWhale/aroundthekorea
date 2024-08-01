<template>
  <h1> main page</h1>

  <button @click="testHello"> call hello api</button>
</template>

<script setup>
import secureBackend from "@/api/authentication.js";

const testHello = () => {
  secureBackend.get("/hello")
      .then((response) => {
        console.log(response);
      });
}

</script>

<style scoped></style>
