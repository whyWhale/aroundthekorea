<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
	<header>
	</header>

	<RouterView />
  <v-bottom-navigation class="wrapper">
    <div>
      <RouterLink :to="{ name: 'main' }">Home</RouterLink>
    </div>
  </v-bottom-navigation>
</template>

<style scoped>
</style>
