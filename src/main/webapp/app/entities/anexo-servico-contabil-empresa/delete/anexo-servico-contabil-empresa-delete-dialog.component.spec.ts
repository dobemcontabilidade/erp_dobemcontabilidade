jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AnexoServicoContabilEmpresaService } from '../service/anexo-servico-contabil-empresa.service';

import { AnexoServicoContabilEmpresaDeleteDialogComponent } from './anexo-servico-contabil-empresa-delete-dialog.component';

describe('AnexoServicoContabilEmpresa Management Delete Component', () => {
  let comp: AnexoServicoContabilEmpresaDeleteDialogComponent;
  let fixture: ComponentFixture<AnexoServicoContabilEmpresaDeleteDialogComponent>;
  let service: AnexoServicoContabilEmpresaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoServicoContabilEmpresaDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AnexoServicoContabilEmpresaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AnexoServicoContabilEmpresaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AnexoServicoContabilEmpresaService);
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
