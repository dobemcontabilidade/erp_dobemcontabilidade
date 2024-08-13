jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AreaContabilContadorService } from '../service/area-contabil-contador.service';

import { AreaContabilContadorDeleteDialogComponent } from './area-contabil-contador-delete-dialog.component';

describe('AreaContabilContador Management Delete Component', () => {
  let comp: AreaContabilContadorDeleteDialogComponent;
  let fixture: ComponentFixture<AreaContabilContadorDeleteDialogComponent>;
  let service: AreaContabilContadorService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AreaContabilContadorDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AreaContabilContadorDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AreaContabilContadorDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AreaContabilContadorService);
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
