jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ServicoContabilOrdemServicoService } from '../service/servico-contabil-ordem-servico.service';

import { ServicoContabilOrdemServicoDeleteDialogComponent } from './servico-contabil-ordem-servico-delete-dialog.component';

describe('ServicoContabilOrdemServico Management Delete Component', () => {
  let comp: ServicoContabilOrdemServicoDeleteDialogComponent;
  let fixture: ComponentFixture<ServicoContabilOrdemServicoDeleteDialogComponent>;
  let service: ServicoContabilOrdemServicoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ServicoContabilOrdemServicoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(ServicoContabilOrdemServicoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ServicoContabilOrdemServicoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ServicoContabilOrdemServicoService);
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
