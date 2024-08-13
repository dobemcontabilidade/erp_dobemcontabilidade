jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ServicoContabilEtapaFluxoModeloService } from '../service/servico-contabil-etapa-fluxo-modelo.service';

import { ServicoContabilEtapaFluxoModeloDeleteDialogComponent } from './servico-contabil-etapa-fluxo-modelo-delete-dialog.component';

describe('ServicoContabilEtapaFluxoModelo Management Delete Component', () => {
  let comp: ServicoContabilEtapaFluxoModeloDeleteDialogComponent;
  let fixture: ComponentFixture<ServicoContabilEtapaFluxoModeloDeleteDialogComponent>;
  let service: ServicoContabilEtapaFluxoModeloService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ServicoContabilEtapaFluxoModeloDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(ServicoContabilEtapaFluxoModeloDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ServicoContabilEtapaFluxoModeloDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ServicoContabilEtapaFluxoModeloService);
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
