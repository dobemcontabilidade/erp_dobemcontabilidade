jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AnexoOrdemServicoExecucaoService } from '../service/anexo-ordem-servico-execucao.service';

import { AnexoOrdemServicoExecucaoDeleteDialogComponent } from './anexo-ordem-servico-execucao-delete-dialog.component';

describe('AnexoOrdemServicoExecucao Management Delete Component', () => {
  let comp: AnexoOrdemServicoExecucaoDeleteDialogComponent;
  let fixture: ComponentFixture<AnexoOrdemServicoExecucaoDeleteDialogComponent>;
  let service: AnexoOrdemServicoExecucaoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoOrdemServicoExecucaoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AnexoOrdemServicoExecucaoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AnexoOrdemServicoExecucaoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AnexoOrdemServicoExecucaoService);
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
