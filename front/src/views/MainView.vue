<template>
	<div class="card-body">
		<h1>{{ appName }}</h1>
		<p>API Base URL: {{ apiUrl }}</p>
		<p>Debug Mode: {{ debugMode }}</p>
	</div>
</template>

<script setup>
const appName = import.meta.env.VITE_APP_NAME
const apiUrl = import.meta.env.VITE_API_BASE_URL
const debugMode = import.meta.env.VITE_DEBUG_MODE
</script>

<style scoped></style>
