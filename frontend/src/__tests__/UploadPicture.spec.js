import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest';
import { mount } from '@vue/test-utils';
import UploadPicture from '../components/UploadPicture.vue';
import genericAvatar from '../assets/generic-avatar.png';

describe('UploadPicture.vue', () => {
  let wrapper;

  // Before each test, mount the component and stub the URL.createObjectURL method
  beforeEach(() => {
    wrapper = mount(UploadPicture);
    // Mock the URL.createObjectURL function to return a predictable URL for testing
    vi.stubGlobal('URL', {createObjectURL: vi.fn(() => 'blob:mock-url')});
  });

  // After each test, clean up the stubbed globals to avoid affecting other tests
  afterEach(() => {
    vi.unstubAllGlobals();
  });

  // Test if the component initially renders with the default generic avatar
  it('renders the default generic avatar', () => {
    const img = wrapper.find('#profile-pic');
    // Verify image element exists in DOM
    expect(img.exists()).toBe(true);
    // Verify image src is set to the imported generic avatar
    expect(img.attributes('src')).toBe(genericAvatar);
  });

  // Test if clicking the image container triggers the file input click function
  it('triggers file input when image is clicked', async () => {
    // Create a spy for the click method
    const mockClick = vi.fn();
    // Get the file input element
    const fileInput = wrapper.find('input[type="file"]').element;
    // Override the click method with our spy
    fileInput.click = mockClick;

    // Set the component's ref to our mocked element
    wrapper.vm.$refs = {fileInput};

    // Trigger click on the image container
    const imageContainer = wrapper.find('.image-container');
    await imageContainer.trigger('click');

    // Verify our spy was called, indicating the file input was triggered
    expect(mockClick).toHaveBeenCalled();
  });

  // Test if selecting a file updates the profile picture
  it('updates the profile picture when a file is selected', async () => {
    // Create a mock file object
    const mockFile = new File(['dummy'], 'avatar.png', {type: 'image/png'});

    // Get the file input element
    const input = wrapper.find('input[type="file"]');

    // Create a synthetic change event with mock file data
    const changeEvent = new Event('change');
    Object.defineProperty(changeEvent, 'target', {
      writable: false,
      value: {files: [mockFile]}
    });

    // Dispatch the change event to the input element
    await input.element.dispatchEvent(changeEvent);
    // Wait for the next tick to allow Vue to process the update
    await wrapper.vm.$nextTick();

    // Verify image src is updated to the blob URL from our mocked createObjectURL
    const updatedImg = wrapper.find('#profile-pic');
    expect(updatedImg.attributes('src')).toBe('blob:mock-url');
  });
});
