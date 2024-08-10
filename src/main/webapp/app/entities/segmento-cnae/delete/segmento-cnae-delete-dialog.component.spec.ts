jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { SegmentoCnaeService } from '../service/segmento-cnae.service';

import { SegmentoCnaeDeleteDialogComponent } from './segmento-cnae-delete-dialog.component';

describe('SegmentoCnae Management Delete Component', () => {
  let comp: SegmentoCnaeDeleteDialogComponent;
  let fixture: ComponentFixture<SegmentoCnaeDeleteDialogComponent>;
  let service: SegmentoCnaeService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SegmentoCnaeDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(SegmentoCnaeDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SegmentoCnaeDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SegmentoCnaeService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
