jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EtapaFluxoExecucaoService } from '../service/etapa-fluxo-execucao.service';

import { EtapaFluxoExecucaoDeleteDialogComponent } from './etapa-fluxo-execucao-delete-dialog.component';

describe('EtapaFluxoExecucao Management Delete Component', () => {
  let comp: EtapaFluxoExecucaoDeleteDialogComponent;
  let fixture: ComponentFixture<EtapaFluxoExecucaoDeleteDialogComponent>;
  let service: EtapaFluxoExecucaoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EtapaFluxoExecucaoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(EtapaFluxoExecucaoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EtapaFluxoExecucaoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EtapaFluxoExecucaoService);
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
