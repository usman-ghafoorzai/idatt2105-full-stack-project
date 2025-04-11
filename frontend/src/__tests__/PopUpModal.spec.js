import { describe, it, expect, vi } from 'vitest';
import { mount } from '@vue/test-utils';
import PopUpModal from '../components/PopUpModal.vue';

describe('PopUpModal.vue', () => {
    it('renders the modal when `isVisible` is true', async () => {
        const wrapper = mount(PopUpModal, {
            props: {
                title: 'Test Modal',
                buttonText: 'Confirm',
            },
        });

        // Initially, the modal should not be visible
        expect(wrapper.find('#popup-modal').exists()).toBe(false);

        // Call the `openModal` method to make it visible
        await wrapper.vm.openModal();
        expect(wrapper.find('#popup-modal').exists()).toBe(true);
    });

    it('closes the modal when the close button is clicked', async () => {
        const wrapper = mount(PopUpModal, {
            props: {
                title: 'Test Modal',
                buttonText: 'Confirm',
            },
        });

        // Open the modal
        await wrapper.vm.openModal();
        expect(wrapper.find('#popup-modal').exists()).toBe(true);

        // Click the close button
        await wrapper.find('.close').trigger('click');
        expect(wrapper.find('#popup-modal').exists()).toBe(false);
    });

    it('closes the modal when the cancel button is clicked', async () => {
        const wrapper = mount(PopUpModal, {
            props: {
                title: 'Test Modal',
                buttonText: 'Confirm',
            },
        });

        // Open the modal
        await wrapper.vm.openModal();
        expect(wrapper.find('#popup-modal').exists()).toBe(true);

        // Click the cancel button
        await wrapper.find('button:nth-of-type(2)').trigger('click');
        expect(wrapper.find('#popup-modal').exists()).toBe(false);
    });

    it('calls the `onConfirm` function when the confirm button is clicked', async () => {
        const onConfirmMock = vi.fn();
        const wrapper = mount(PopUpModal, {
            props: {
                title: 'Test Modal',
                buttonText: 'Confirm',
                onConfirm: onConfirmMock,
            },
        });

        // Open the modal
        await wrapper.vm.openModal();
        expect(wrapper.find('#popup-modal').exists()).toBe(true);

        // Click the confirm button
        await wrapper.find('button:nth-of-type(1)').trigger('click');
        expect(onConfirmMock).toHaveBeenCalled();
        expect(wrapper.find('#popup-modal').exists()).toBe(false); // Modal should close after confirmation
    });

    it('renders the correct title and button text', async () => {
        const wrapper = mount(PopUpModal, {
            props: {
                title: 'Test Modal',
                buttonText: 'Confirm',
            },
        });

        // Open the modal
        await wrapper.vm.openModal();

        // Check the title and button text
        expect(wrapper.find('h2').text()).toBe('Test Modal');
        expect(wrapper.find('button:nth-of-type(1)').exists()).toBe(true);
    });
});