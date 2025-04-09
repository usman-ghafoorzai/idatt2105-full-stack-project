import { mount } from '@vue/test-utils';
import { describe, it, expect } from 'vitest';
import SellerInfo from '../components/SellerInfo.vue';

describe('SellerInfo.vue', () => {
    const mockSeller = {
        name: 'John Doe',
        phoneNumber: '987 65 432',
        email: 'test@gmail.com', 
    };

    it('renders the sellers name', () => {
        const wrapper = mount(SellerInfo, {
            props: mockSeller,
        });
        expect(wrapper.find('#seller-info div:nth-of-type(1)').text()).toContain(mockSeller.name);
    });

    it('renders the sellers phonenumber', () => {
        const wrapper = mount(SellerInfo, {
            props: mockSeller,
        });
        expect(wrapper.find('#seller-info div:nth-of-type(2)').text()).toContain(mockSeller.phoneNumber);
    });

    it('renders the sellers email', () => {
        const wrapper = mount(SellerInfo, {
            props: mockSeller,
        });
        expect(wrapper.find('#seller-info div:nth-of-type(3)').text()).toContain(mockSeller.email);
    });
});