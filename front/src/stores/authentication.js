import {defineStore} from 'pinia'
import {computed, ref} from 'vue'

export const AuthenticationStore = defineStore('auth', () => {
    const token = ref(import.meta.env.VITE_TEMPORARY_TOKEN)
    const certify = computed(() => token.value)

    const replace = (newToken) => {
        token.value = newToken
    }

    return {token, certify, replace}
},)
