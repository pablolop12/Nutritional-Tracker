import { TestBed } from '@angular/core/testing';

import { ConsumptionNotifierService } from './consumption-notifier.service';

describe('ConsumptionNotifierService', () => {
  let service: ConsumptionNotifierService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConsumptionNotifierService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
