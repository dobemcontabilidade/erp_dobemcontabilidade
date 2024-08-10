import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PlanoContaAzulDetailComponent } from './plano-conta-azul-detail.component';

describe('PlanoContaAzul Management Detail Component', () => {
  let comp: PlanoContaAzulDetailComponent;
  let fixture: ComponentFixture<PlanoContaAzulDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlanoContaAzulDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PlanoContaAzulDetailComponent,
              resolve: { planoContaAzul: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PlanoContaAzulDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlanoContaAzulDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load planoContaAzul on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PlanoContaAzulDetailComponent);

      // THEN
      expect(instance.planoContaAzul()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
