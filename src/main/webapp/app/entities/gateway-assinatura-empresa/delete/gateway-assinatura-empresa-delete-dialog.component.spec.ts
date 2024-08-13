jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { GatewayAssinaturaEmpresaService } from '../service/gateway-assinatura-empresa.service';

import { GatewayAssinaturaEmpresaDeleteDialogComponent } from './gateway-assinatura-empresa-delete-dialog.component';

describe('GatewayAssinaturaEmpresa Management Delete Component', () => {
  let comp: GatewayAssinaturaEmpresaDeleteDialogComponent;
  let fixture: ComponentFixture<GatewayAssinaturaEmpresaDeleteDialogComponent>;
  let service: GatewayAssinaturaEmpresaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GatewayAssinaturaEmpresaDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(GatewayAssinaturaEmpresaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GatewayAssinaturaEmpresaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(GatewayAssinaturaEmpresaService);
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
