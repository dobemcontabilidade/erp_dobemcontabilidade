jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AnexoRequeridoServicoContabilService } from '../service/anexo-requerido-servico-contabil.service';

import { AnexoRequeridoServicoContabilDeleteDialogComponent } from './anexo-requerido-servico-contabil-delete-dialog.component';

describe('AnexoRequeridoServicoContabil Management Delete Component', () => {
  let comp: AnexoRequeridoServicoContabilDeleteDialogComponent;
  let fixture: ComponentFixture<AnexoRequeridoServicoContabilDeleteDialogComponent>;
  let service: AnexoRequeridoServicoContabilService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoRequeridoServicoContabilDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AnexoRequeridoServicoContabilDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AnexoRequeridoServicoContabilDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AnexoRequeridoServicoContabilService);
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
