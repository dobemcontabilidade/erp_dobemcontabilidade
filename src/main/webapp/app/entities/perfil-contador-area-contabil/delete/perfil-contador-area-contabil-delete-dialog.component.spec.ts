jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PerfilContadorAreaContabilService } from '../service/perfil-contador-area-contabil.service';

import { PerfilContadorAreaContabilDeleteDialogComponent } from './perfil-contador-area-contabil-delete-dialog.component';

describe('PerfilContadorAreaContabil Management Delete Component', () => {
  let comp: PerfilContadorAreaContabilDeleteDialogComponent;
  let fixture: ComponentFixture<PerfilContadorAreaContabilDeleteDialogComponent>;
  let service: PerfilContadorAreaContabilService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PerfilContadorAreaContabilDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(PerfilContadorAreaContabilDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PerfilContadorAreaContabilDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PerfilContadorAreaContabilService);
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
