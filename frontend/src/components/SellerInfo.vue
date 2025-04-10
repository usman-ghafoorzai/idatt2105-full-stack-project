<script setup>
    import PopUpModal from './PopUpModal.vue';
    import { ref } from 'vue';

    const modalRef = ref(null);
    const modalTitle = ref('Contact Seller');
    const modalButtonText = ref('Contact through website');

    function openModal() {
        modalRef.value.openModal();
    }

    defineProps({
        name: String,
        phoneNumber: String,
        email: String,
    })

</script>

<template>
    <div id="seller-info-container">
        <h2>Seller Information</h2>
        <div id="seller-info">
            <div><strong>Name:</strong> {{ name }}</div>
            <div><strong>Phone Number:</strong> {{ phoneNumber }}</div>
            <div><strong>Email:</strong> {{ email }}</div>
        </div>
        <button @click="openModal">
            Contact seller
        </button>
        <PopUpModal
            ref="modalRef"
            :title="modalTitle"
            :buttonText="modalButtonText"
            :onConfirm="() => console.log('Confirmed contact with seller')"
        />
    </div>
</template>

<style scoped>
    #seller-info-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        width: 100%;
        background-color: #FFFBFB;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
    #seller-info {
        display: grid;
        grid-template-rows: auto auto auto;
        gap: 5px;
        margin-left: 20px;
        width: 100%;
    }

    button {
        font-size: 20px;
        align-self: flex-end;
        width: 40%;
        margin: 20px 10px;
    }

</style>