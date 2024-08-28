import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { EnquadramentoService } from '../service/enquadramento.service';
import { IEnquadramento } from '../enquadramento.model';
import { EnquadramentoFormService } from './enquadramento-form.service';

import { EnquadramentoUpdateComponent } from './enquadramento-update.component';

describe('Enquadramento Management Update Component', () => {
  let comp: EnquadramentoUpdateComponent;
  let fixture: ComponentFixture<EnquadramentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let enquadramentoFormService: EnquadramentoFormService;
  let enquadramentoService: EnquadramentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EnquadramentoUpdateComponent],
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
      .overrideTemplate(EnquadramentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EnquadramentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    enquadramentoFormService = TestBed.inject(EnquadramentoFormService);
    enquadramentoService = TestBed.inject(EnquadramentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const enquadramento: IEnquadramento = { id: 456 };

      activatedRoute.data = of({ enquadramento });
      comp.ngOnInit();

      expect(comp.enquadramento).toEqual(enquadramento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnquadramento>>();
      const enquadramento = { id: 123 };
      jest.spyOn(enquadramentoFormService, 'getEnquadramento').mockReturnValue(enquadramento);
      jest.spyOn(enquadramentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enquadramento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enquadramento }));
      saveSubject.complete();

      // THEN
      expect(enquadramentoFormService.getEnquadramento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(enquadramentoService.update).toHaveBeenCalledWith(expect.objectContaining(enquadramento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnquadramento>>();
      const enquadramento = { id: 123 };
      jest.spyOn(enquadramentoFormService, 'getEnquadramento').mockReturnValue({ id: null });
      jest.spyOn(enquadramentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enquadramento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enquadramento }));
      saveSubject.complete();

      // THEN
      expect(enquadramentoFormService.getEnquadramento).toHaveBeenCalled();
      expect(enquadramentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnquadramento>>();
      const enquadramento = { id: 123 };
      jest.spyOn(enquadramentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enquadramento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(enquadramentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
