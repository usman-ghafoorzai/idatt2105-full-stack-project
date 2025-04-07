<script setup>
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
        <button>
            Contact seller
        </button>
    </div>
</template>

<style scoped>
    #seller-info-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        width: 20%;
        padding: 20px;
        background-color: #FFFBFB;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
    #seller-info {
        display: grid;
        grid-template-rows: auto auto auto;
        gap: 5px;
        width: 100%;
    }

    button {
        border-radius: 10px;
        font-size: 20px;
        align-self: flex-end;
        background-color: #88A6E6;
        width: 40%;
        padding: 10px 0;
        margin-top: 10px;
    }

    button:hover {
        background-color: #5272a1;
        cursor: pointer;
    }

</style>