<script setup>
    import { ref } from 'vue';

    const props = defineProps({
        title: String,
        onConfirm: Function,
        buttonText: String,
    });

    const isVisible = ref(false);

    function openModal() {
        isVisible.value = true;
    }
    function closeModal() {
        isVisible.value = false;
    }
    function confirmAction() {
        if (props.onConfirm) {
            props.onConfirm();
        }
        closeModal();
    }

    defineExpose({
        openModal,
        closeModal,
    });
</script>

<template>
    <div id="popup-modal" v-if="isVisible">
        <div class="modal-overlay" @click="closeModal"></div>
        <div class="modal-content">
            <span class="close" @click="closeModal">&times;</span>
            <h2>{{ title }}</h2>
            <div class="modal-buttons">
                <button @click="confirmAction">{{ buttonText }}</button>
                <button @click="closeModal">Cancel</button>
            </div>
        </div>
    </div>
</template>

<style scoped>
#popup-modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}
.modal-overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
}
.modal-content {
    position: relative;
    background: white;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    z-index: 1001;
    text-align: center;
}
.close {
    position: absolute;
    top: 10px;
    right: 10px;
    font-size: 20px;
    cursor: pointer;
}
.modal-buttons {
    display: flex;
    justify-content: center;
    gap: 10px;
    margin-top: 20px;
}
</style>