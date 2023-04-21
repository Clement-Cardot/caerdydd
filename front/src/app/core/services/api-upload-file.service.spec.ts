import { TestBed } from '@angular/core/testing';

import { ApiUploadFileService } from './api-upload-file.service';

describe('ApiUploadFileService', () => {
  let service: ApiUploadFileService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApiUploadFileService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
