import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { GatewayPagamentoService } from '../service/gateway-pagamento.service';
import { IGatewayPagamento } from '../gateway-pagamento.model';
import { GatewayPagamentoFormService } from './gateway-pagamento-form.service';

import { GatewayPagamentoUpdateComponent } from './gateway-pagamento-update.component';

describe('GatewayPagamento Management Update Component', () => {
  let comp: GatewayPagamentoUpdateComponent;
  let fixture: ComponentFixture<GatewayPagamentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let gatewayPagamentoFormService: GatewayPagamentoFormService;
  let gatewayPagamentoService: GatewayPagamentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GatewayPagamentoUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(GatewayPagamentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GatewayPagamentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    gatewayPagamentoFormService = TestBed.inject(GatewayPagamentoFormService);
    gatewayPagamentoService = TestBed.inject(GatewayPagamentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const gatewayPagamento: IGatewayPagamento = { id: 456 };

      activatedRoute.data = of({ gatewayPagamento });
      comp.ngOnInit();

      expect(comp.gatewayPagamento).toEqual(gatewayPagamento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGatewayPagamento>>();
      const gatewayPagamento = { id: 123 };
      jest.spyOn(gatewayPagamentoFormService, 'getGatewayPagamento').mockReturnValue(gatewayPagamento);
      jest.spyOn(gatewayPagamentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gatewayPagamento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: gatewayPagamento }));
      saveSubject.complete();

      // THEN
      expect(gatewayPagamentoFormService.getGatewayPagamento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(gatewayPagamentoService.update).toHaveBeenCalledWith(expect.objectContaining(gatewayPagamento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGatewayPagamento>>();
      const gatewayPagamento = { id: 123 };
      jest.spyOn(gatewayPagamentoFormService, 'getGatewayPagamento').mockReturnValue({ id: null });
      jest.spyOn(gatewayPagamentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gatewayPagamento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: gatewayPagamento }));
      saveSubject.complete();

      // THEN
      expect(gatewayPagamentoFormService.getGatewayPagamento).toHaveBeenCalled();
      expect(gatewayPagamentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGatewayPagamento>>();
      const gatewayPagamento = { id: 123 };
      jest.spyOn(gatewayPagamentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gatewayPagamento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(gatewayPagamentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
