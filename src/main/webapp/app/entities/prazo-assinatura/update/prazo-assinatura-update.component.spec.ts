import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { PrazoAssinaturaService } from '../service/prazo-assinatura.service';
import { IPrazoAssinatura } from '../prazo-assinatura.model';
import { PrazoAssinaturaFormService } from './prazo-assinatura-form.service';

import { PrazoAssinaturaUpdateComponent } from './prazo-assinatura-update.component';

describe('PrazoAssinatura Management Update Component', () => {
  let comp: PrazoAssinaturaUpdateComponent;
  let fixture: ComponentFixture<PrazoAssinaturaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let prazoAssinaturaFormService: PrazoAssinaturaFormService;
  let prazoAssinaturaService: PrazoAssinaturaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PrazoAssinaturaUpdateComponent],
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
      .overrideTemplate(PrazoAssinaturaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrazoAssinaturaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    prazoAssinaturaFormService = TestBed.inject(PrazoAssinaturaFormService);
    prazoAssinaturaService = TestBed.inject(PrazoAssinaturaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const prazoAssinatura: IPrazoAssinatura = { id: 456 };

      activatedRoute.data = of({ prazoAssinatura });
      comp.ngOnInit();

      expect(comp.prazoAssinatura).toEqual(prazoAssinatura);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrazoAssinatura>>();
      const prazoAssinatura = { id: 123 };
      jest.spyOn(prazoAssinaturaFormService, 'getPrazoAssinatura').mockReturnValue(prazoAssinatura);
      jest.spyOn(prazoAssinaturaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prazoAssinatura });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prazoAssinatura }));
      saveSubject.complete();

      // THEN
      expect(prazoAssinaturaFormService.getPrazoAssinatura).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(prazoAssinaturaService.update).toHaveBeenCalledWith(expect.objectContaining(prazoAssinatura));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrazoAssinatura>>();
      const prazoAssinatura = { id: 123 };
      jest.spyOn(prazoAssinaturaFormService, 'getPrazoAssinatura').mockReturnValue({ id: null });
      jest.spyOn(prazoAssinaturaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prazoAssinatura: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prazoAssinatura }));
      saveSubject.complete();

      // THEN
      expect(prazoAssinaturaFormService.getPrazoAssinatura).toHaveBeenCalled();
      expect(prazoAssinaturaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrazoAssinatura>>();
      const prazoAssinatura = { id: 123 };
      jest.spyOn(prazoAssinaturaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prazoAssinatura });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(prazoAssinaturaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
